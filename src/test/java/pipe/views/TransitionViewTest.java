package pipe.views;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: st809
 * Date: 09/10/2013
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class TransitionViewTest {
    private TransitionView view;

    // x and y values for view initialisation
    private static final int x = 0;
    private static final int y = 0;
    private static final String name = "Test";

    @Before
    public void setup()
    {
        view = new TransitionView(x, y);
        view.setName(name);
    }

    @Test
    public void successfulNameOnFirstPaste()
    {
        //TODO This really needs to be mocked
        PetriNetView mockPetriNet = mock(PetriNetView.class);
        when(mockPetriNet.checkTransitionIDAvailability(anyString())).thenReturn(true);

        TransitionView newView = view.paste(x, y, false, mockPetriNet);
        assertEquals(name + "(0)", newView.getName());
    }

    @Test
    public void successfulNameOnSecondID()
    {
        //TODO This really needs to be mocked
        PetriNetView mockPetriNet = mock(PetriNetView.class);
        when(mockPetriNet.checkTransitionIDAvailability(anyString())).thenReturn(false)
                                                                     .thenReturn(true);

        TransitionView newView = view.paste(x, y, false, mockPetriNet);
        assertEquals(name + "(0)'", newView.getName());
    }
}
