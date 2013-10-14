package pipe.views;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.w3c.dom.Document;
import pipe.models.PetriNet;
import pipe.utilities.transformers.PNMLTransformer;
import pipe.utilities.transformers.PNMLTransformerTest;

public class PetriNetViewTest
{

	private PetriNetView petriNetView;
	private PlaceView placeView;
	private MarkingView markingView;
    private PetriNet mockModel;

	@Before
	public void setUp() throws Exception
	{
        mockModel = Mockito.mock(PetriNet.class);
		petriNetView = new PetriNetView(null, mockModel);
	}
//    TODO: SHould test String constructor but plan to remove it
    @Test
    public void registersSelfAsModelObserver()
    {
        PetriNetView view = new PetriNetView(null, mockModel);
        verify(mockModel, times(1)).registerObserver(view);
    }
    @Test
	public void verifyAnimationModePossibleForNewPetriNewViewWhenNetFileHasToken() throws Exception
	{
		checkAnimationModePossibleForNewPetriNet(PNMLTransformerTest.SIMPLE_NET_WITH_TOKEN); 
	}
	@Test
	public void verifyAnimationModePossibleForNewPetriNetViewWhenNetFileDoesNotHaveToken() throws Exception
	{
		checkAnimationModePossibleForNewPetriNet(PNMLTransformerTest.SIMPLE_NET_WITHOUT_TOKEN); 
	}
	protected void checkAnimationModePossibleForNewPetriNet(String net)
			throws TransformerFactoryConfigurationError
	{
		assertNotNull("not null at creation",petriNetView.getTokenViews());
		buildPetriNetViewFromXmlString(net); 
		assertNotNull("emptied, but token set controller re-created",petriNetView.getTokenViews());
		assertNull("no matrices created yet",petriNetView.getTokenViews().get(0).getForwardsIncidenceMatrix()); 
		petriNetView.setEnabledTransitions(); 
		assertNotNull("matrices should be created now",petriNetView.getTokenViews().get(0).getForwardsIncidenceMatrix());
	}
	protected void buildPetriNetViewFromXmlString(String net)
			throws TransformerFactoryConfigurationError
	{
        PNMLTransformer transformer = new PNMLTransformer();
        Document doc = transformer.transformPNMLStreamSource(PNMLTransformerTest
                .getNetAsStreamSource(net));
        when(mockModel.getXMLDocument(anyBoolean())).thenReturn(doc);
        petriNetView.createFromPNML(false);
	}
	@Test
	public void verifyInitialPlaceMarkingIgnoredIfCorrespondingTokenDoesNotExist() throws Exception
	{
		buildPetriNetViewFromXmlString(PNMLTransformerTest.ONE_TOKEN_TWO_INITIAL_MARKINGS);
		assertEquals(1, petriNetView.getTokenViews().size());
		assertEquals(1, petriNetView.getPlacesArrayList().size());
		placeView = petriNetView.getPlacesArrayList().get(0);
		assertEquals("only one MarkingView created",1, placeView.getCurrentMarkingView().size());
		markingView = placeView.getCurrentMarkingView().get(0); 
		assertEquals(petriNetView.getTokenViews().get(0), markingView.getToken());
	}
	@Test
	public void verifyNonzeroInitialMarkingLocksCorrespondingTokenView() throws Exception
	{
		buildPetriNetViewFromXmlString(PNMLTransformerTest.TWO_TOKENS_TWO_INITIAL_MARKINGS_ONE_NONZERO);
		assertEquals(2, petriNetView.getTokenViews().size());
		assertEquals(1, petriNetView.getPlacesArrayList().size());
		placeView = petriNetView.getPlacesArrayList().get(0);
		assertEquals("two MarkingViews",2, placeView.getCurrentMarkingView().size());
		markingView = placeView.getCurrentMarkingView().get(0);
		assertEquals(0, markingView.getCurrentMarking()); 
		assertFalse(markingView.getToken().isLocked());
		assertEquals(petriNetView.getTokenViews().get(0),markingView.getToken());
		markingView = placeView.getCurrentMarkingView().get(1);
		assertEquals(1, markingView.getCurrentMarking()); 
		assertTrue(markingView.getToken().isLocked());
		assertEquals(petriNetView.getTokenViews().get(1),markingView.getToken());
	}
	@Test
	public void verifyDefaultTokenViewCreatedDuringConstruction() throws Exception
	{
		assertEquals("Default created",1, petriNetView.getTokenViews().size());
		assertEquals("Default", petriNetView.getTokenViews().get(0).getID());
	}
}
