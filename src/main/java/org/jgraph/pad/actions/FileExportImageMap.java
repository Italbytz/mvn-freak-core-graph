/*
 * @(#)FileExportGXL.java	1.2 01.02.2003
 *
 * Copyright (C) 2003 sven.luzar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package org.jgraph.pad.actions;

import org.jgraph.GPGraphpad;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.pad.EllipseCell;
import org.jgraph.pad.GPConverter;
import org.jgraph.pad.GPGraph;
import org.jgraph.pad.GPUserObject;
import org.jgraph.pad.resources.Translator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author sven.luzar
 * @version 1.0
 *
 */
public class FileExportImageMap extends AbstractActionFile {

	/**
	 * Constructor for FileExportGXL.
	 * @param graphpad
	 */
	public FileExportImageMap(GPGraphpad graphpad) {
		super(graphpad);
	}


	/**
	 * Override or implement to map from cells to urls.
	 * @param cell Cell that should be converted to a URL
	 * @return String String that can be used as a href
	 */
	public String getURL(GPGraph graph, Object cell) {
		if (cell instanceof DefaultGraphCell) {
			final Object userObject = ((DefaultGraphCell) cell).getUserObject();
			if (userObject instanceof GPUserObject) {
				Object url = ((GPUserObject) userObject).getProperty(GPUserObject.keyURI);
				if (url != null)
					return url.toString();
			}
		}
		return cell.toString();
	}

	/**
	 * Override or implement to map from cells to labels.
	 * @param cell Cell that should be converted to a label
	 * @return String String that can be used as a label
	 */
	public String getLabel(GPGraph graph, Object cell) {
		return graph.convertValueToString(cell.toString());
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {

			if (getCurrentDocument().getModel().getRootCount() > 0) {
				// JPG Image
				String fileType = "jpg";
				String imageFile =
					saveDialog(
						fileType.toUpperCase()+" Image "+
						Translator.getString("FileSaveAsLabel"),
						fileType.toLowerCase(),
						fileType.toUpperCase() + " Image");
				if (imageFile == null)
					return;
				BufferedImage img = null;
				img = GPConverter.toImage(getCurrentGraph());
				ImageIO.write(img, fileType.toLowerCase(), new File(imageFile));
				// HTML File
				if (img != null) {
					fileType = "html";
					String htmlFile =
						saveDialog(
							fileType.toUpperCase()+" File "+
							Translator.getString("FileSaveAsLabel"),
							fileType.toLowerCase(),
							fileType.toUpperCase() + " File");
					if (htmlFile == null)
						return;
					FileOutputStream fos = new FileOutputStream(htmlFile);
					String imageTag =
						"<img src=\""
							+ imageFile
							+ "\" width=\""
							+ img.getWidth()
							+ "\" height=\""
							+ img.getHeight()
							+ "\" border=\"0\" ismap usemap=\"#map\">\n";
					fos.write(imageTag.getBytes());
					fos.write("<map NAME=\"map\">\n".getBytes());
					GPGraph graph = super.getCurrentGraph();
					Rectangle bounds = graph.getCellBounds(graph.getRoots());
					Object[] vertices = graph.getVertices(graph.getAll());
					for (int i = 0; i < vertices.length; i++) {
						String alt = getLabel(graph, vertices[i]);
						String href = getURL(graph, vertices[i]);
						CellView view =
							graph.getGraphLayoutCache().getMapping(
								vertices[i],
								false);
						if (view != null && href != null) {
							Rectangle b =
								graph.toScreen(new Rectangle(view.getBounds()));
							b.translate(-bounds.x + 5, -bounds.y + 5);
							String rect =
								b.x
									+ ","
									+ b.y
									+ ","
									+ (int) (b.x + b.width)
									+ ","
									+ (int) (b.y + b.height);
							String shape =
								(vertices[i] instanceof EllipseCell)
									? "circle"
									: "rect";
							String map =
								"<area shape="
									+ shape
									+ " coords=\""
									+ rect
									+ "\" href=\""
									+ href
									+ "\" alt=\""
									+ alt
									+ "\">\n";
							fos.write(map.getBytes());
						} // if view != null
					} // for
					fos.write("</map>".getBytes());
					fos.flush();
					fos.close();
				} // if img != null
			}
		} catch (IOException ex) {
			graphpad.error(ex.getMessage());
		}

	}

}
