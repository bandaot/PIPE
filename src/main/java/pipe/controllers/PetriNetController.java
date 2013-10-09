package pipe.controllers;

import pipe.controllers.interfaces.IController;
import pipe.models.PetriNet;
import pipe.views.PetriNetView;

import java.io.Serializable;
import java.util.ArrayList;

public class PetriNetController implements IController, Serializable
{

    private ArrayList<PetriNetView> _views = new ArrayList<PetriNetView>();
    private ArrayList<PetriNet> _models = new ArrayList<PetriNet>();
    private int _activePetriNet = 0;

    public PetriNetController()
    {
        PlaceController placeController = new PlaceController();
        TransitionController transitionController = new TransitionController();
        TokenController tokenController = new TokenController();
        MarkingController markingController = new MarkingController(tokenController);
    }

    public PetriNetView getView()
    {
        return _views.get(_activePetriNet);
    }


    public PetriNetView addEmptyPetriNet()
    {
        PetriNet petriNet = new PetriNet();
        PetriNetView petriNetView = new PetriNetView(this, petriNet);
        _views.add(petriNetView);
        _models.add(petriNet);
        changeActivePetriNet();
        return petriNetView;
    }

    private void changeActivePetriNet()
    {
        _activePetriNet = _models.size() - 1;
    }
}
