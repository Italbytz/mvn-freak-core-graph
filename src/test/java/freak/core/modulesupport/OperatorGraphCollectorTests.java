package freak.core.modulesupport;

import edu.cornell.lassp.houle.RngPack.RandomElement;
import freak.core.control.*;
import freak.core.event.BatchEventListener;
import freak.core.event.GenerationEventListener;
import freak.core.event.RunEventListener;
import freak.core.event.ScheduleEventListener;
import freak.core.fitness.FitnessFunction;
import freak.core.graph.FreakGraphModelInterface;
import freak.core.graph.Initialization;
import freak.core.graph.OperatorGraph;
import freak.core.mapper.Mapper;
import freak.core.observer.ObserverManagerInterface;
import freak.core.populationmanager.PopulationManager;
import freak.core.random.SimpleRandomElementFactory;
import freak.core.searchspace.SearchSpace;
import freak.core.stoppingcriterion.StoppingCriterion;
import freak.module.searchspace.PointSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class OperatorGraphCollectorTests {

    @Test
    public void testGetResourceListing() {
        ScheduleInterface schedule = new MockSchedule();
        OperatorGraphCollector collector = new OperatorGraphCollector(schedule);
        ModuleInfo[] graphs =  collector.getPredefinedGraphs("LTSGraph.fop");
        Assertions.assertNotNull(graphs);
        Assertions.assertTrue(graphs.length > 0);
    }

    private class MockSchedule implements ScheduleInterface {

        @Override
        public int getCurrentGeneration() {
            return 0;
        }

        @Override
        public RandomElement getRandomElement() {
            SimpleRandomElementFactory randomElementFactory = new SimpleRandomElementFactory();
            return randomElementFactory.getRandomElement();
        }

        @Override
        public SearchSpace getPhenotypeSearchSpace() {
            return null;
        }

        @Override
        public FitnessFunction getFitnessFunction() {
            return null;
        }

        @Override
        public EventController getEventController() {
            return null;
        }

        @Override
        public void triggerStopCriterion() {

        }

        @Override
        public Mapper getMapper() {
            return null;
        }

        @Override
        public SearchSpace getGenotypeSearchSpace() {
            return new PointSet(this);
        }

        @Override
        public GenerationIndex getNextStepTimeIndex() {
            return null;
        }

        @Override
        public GenerationIndex getCurrentTimeIndex() {
            return null;
        }

        @Override
        public void step() {

        }

        @Override
        public BatchList getBatchList() {
            return null;
        }

        @Override
        public boolean isLastRunInBatch() {
            return false;
        }

        @Override
        public boolean isLastBatch() {
            return false;
        }

        @Override
        public void skip() {

        }

        @Override
        public boolean isCurrentRunFinished() {
            return false;
        }

        @Override
        public ObserverManagerInterface getObserverManager() {
            return null;
        }

        @Override
        public int getCurrentBatch() {
            return 0;
        }

        @Override
        public int getCurrentRun() {
            return 0;
        }

        @Override
        public void callInitialize() {

        }

        @Override
        public PopulationManager getPopulationManager() {
            return null;
        }

        @Override
        public FitnessFunction getRealFitnessFunction() {
            return null;
        }

        @Override
        public void modulesEdited() {

        }

        @Override
        public double getRunTime() {
            return 0;
        }

        @Override
        public Batch getDefaultBatch() {
            return null;
        }

        @Override
        public Initialization getInitialization() {
            return null;
        }

        @Override
        public OperatorGraph getOperatorGraph() {
            return null;
        }

        @Override
        public StoppingCriterion[] getStoppingCriteria() {
            return new StoppingCriterion[0];
        }

        @Override
        public Batch createBatchFromCurrentConfigurations() {
            return null;
        }

        @Override
        public void setDefaultBatch(Batch batch) {

        }

        @Override
        public void setGraphModel(FreakGraphModelInterface graphModel) {

        }

        @Override
        public void setFitnessFunction(FitnessFunction function) {

        }

        @Override
        public void setMapper(Mapper mapper) {

        }

        @Override
        public void setPhenotypeSearchSpace(SearchSpace space) {

        }

        @Override
        public void setPopulationManager(PopulationManager manager) {

        }

        @Override
        public void setStoppingCriteria(StoppingCriterion[] criteria) {

        }

        @Override
        public void setInitialization(Initialization initialization) {

        }

        @Override
        public Set getAllModules() {
            return null;
        }

        @Override
        public FreakGraphModelInterface getGraphModel() {
            return null;
        }

        @Override
        public Object[] getPossibleEventSources() {
            return new Object[0];
        }

        @Override
        public FreakGraphModelInterface getFreakGraphModel() {
            return null;
        }

        @Override
        public void addBatchEventListener(BatchEventListener l) {

        }

        @Override
        public void removeBatchEventListener(BatchEventListener l) {

        }

        @Override
        public void addGenerationEventListener(GenerationEventListener l) {

        }

        @Override
        public void removeGenerationEventListener(GenerationEventListener l) {

        }

        @Override
        public void addRunEventListener(RunEventListener l) {

        }

        @Override
        public void removeRunEventListener(RunEventListener l) {

        }

        @Override
        public void addScheduleEventListener(ScheduleEventListener l) {

        }

        @Override
        public void removeScheduleEventListener(ScheduleEventListener l) {

        }
    }
}
