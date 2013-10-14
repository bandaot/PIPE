package pipe.controllers;

import pipe.gui.ApplicationSettings;
import pipe.gui.CopyPasteManager;
import pipe.gui.PetriNetTab;
import pipe.models.PetriNet;
import pipe.models.PipeApplicationModel;
import pipe.views.PetriNetView;
import pipe.views.PipeApplicationView;

import java.util.ArrayList;

public class PipeApplicationController
{
    private PetriNetController _petriNetController;
    private final CopyPasteManager _copyPasteManager;
    private final PipeApplicationModel _applicationModel;
    private PipeApplicationView _applicationView;

    /**
     * Launches a new PipeApplicationView
     * @param applicationModel
     */
    public PipeApplicationController(PipeApplicationModel applicationModel)
    {
        _petriNetController = new PetriNetController();
        _applicationModel = applicationModel;
        _copyPasteManager = new CopyPasteManager();
        ApplicationSettings.register(this);
        _applicationView = new PipeApplicationView(this, _applicationModel);
    }

    public CopyPasteManager getCopyPasteManager()
    {
        return _copyPasteManager;
    }

    public boolean isPasteEnabled()
    {
        return _copyPasteManager.pasteEnabled();
    }

    public boolean isPasteInProgress()
    {
        return _copyPasteManager.pasteInProgress();
    }

    public void cancelPaste()
    {
        _copyPasteManager.cancelPaste();
    }

    public void copy(ArrayList selection, PetriNetTab appView)
    {
        _copyPasteManager.doCopy(selection, appView);
    }

    public void showPasteRectangle(PetriNetTab appView)
    {
        _copyPasteManager.showPasteRectangle(appView);
    }

    public PetriNetView createEmptyPetriNetView()
    {
        return _petriNetController.addEmptyPetriNet();
    }

    public PetriNetView createPetriNetViewFromFile(String path)
    {
        return _petriNetController.createPetriNetViewFromFile(path);
    }

    public PetriNetTab createNewTab(PetriNetView petriNetView)
    {
        PetriNetTab tab = new PetriNetTab(petriNetView);
        petriNetView.addObserver(tab); // Add the view as Observer
        petriNetView.addObserver(ApplicationSettings.getApplicationView()); // Add the app window as observer
        return tab;
    }


    public int addEmptyPetriNetTo(ArrayList<PetriNetTab> petriNetTabs)
    {
        PetriNetView petriNetView = _petriNetController.addEmptyPetriNet();
        PetriNetTab petriNetTab = new PetriNetTab(petriNetView);
        petriNetTabs.add(petriNetTab);
        return petriNetTabs.size() - 1;
    }

    public PetriNetController getPetriNetController()
    {
        return _petriNetController;
    }
}
