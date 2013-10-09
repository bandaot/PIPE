package pipe.views;

import parser.ExprEvaluator;
import parser.MarkingDividedByNumberException;
import pipe.controllers.MarkingController;
import pipe.models.Marking;
import pipe.models.PipeObservable;
import pipe.models.interfaces.IObserver;

import javax.swing.*;

import net.sourceforge.jeval.EvaluationException;

import java.awt.*;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

//  Steve Doubleday:  changed from IObserver to Observer interface to make use of 
//  the update(Observable Object) method for TokenViews

public class MarkingView extends JComponent implements Serializable, Observer
{
    private TokenView _tokenView;
    private final Marking _model;
    private PipeObservable _pipeObservable = new PipeObservable(this);

    private MarkingView(TokenView tokenView, Marking model)
    {
        _model = model;
        _tokenView = tokenView;
        _tokenView.addObserver(this);
    }

    public MarkingView(MarkingController controller, Marking model)
    {
        this(new TokenView(controller.getTokenController(), model.getToken()),
             model);
    }

    public MarkingView(TokenView tokenView, String marking)
    {
        this(tokenView, new Marking(tokenView.getModel(), marking));
    }
    

    public MarkingView(TokenView tokenView, int marking)
    {
        this(tokenView, Integer.toString(marking));
    }

    public TokenView getToken()
    {
        return _tokenView;
    }

    public void setToken(TokenView tokenView)
    {
        _tokenView = tokenView;
        if (_tokenView != null) 
        {
        	_tokenView.addObserver(this); 
        	_model.setToken(tokenView.getModel());
        }
    }

    public void setCurrentMarking(int marking)
    {
        _model.setCurrentMarking(marking);
    }
    public void setCurrentMarking(String marking)
    {
        _model.setCurrentMarking(marking);
    }

    public int getCurrentMarking()
    {
		try {
			return Integer.parseInt(_model.getCurrentMarking());
		} catch (NumberFormatException e) {
			
			ExprEvaluator paser = new ExprEvaluator();
			int result;
			try {
				result = paser.parseAndEvalExpr(_model.getCurrentMarking(),_model.getToken().getId());
			} catch (EvaluationException e1) {
    			return showErrorMessage();
			} catch (MarkingDividedByNumberException e1) {
				return showErrorMessage();
			}	catch(Exception e1){
				return showErrorMessage();
			}
			return result;
		}
	}
    
    private int showErrorMessage(){
    	String message = "Errors in marking-dependent arc weight expression."+
				"\r\n The computation should be aborted";
        String title = "Error";
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        return -1;
    }
    
    public String getCurrentFunctionalMarking(){
    	return _model.getCurrentMarking();
    }


    public void update(Graphics canvas, Insets insets, int count, int tempTotalMarking)
    {
        _tokenView.update(canvas,insets,count, tempTotalMarking, getCurrentMarking());
    }

	@Override
	public void update(Observable oldObj, Object newObj)
	{
		if (oldObj instanceof TokenView) 
		{
			if (newObj == null) setToken(null);  
			else if (newObj instanceof TokenView) setToken((TokenView) newObj);  
			setChanged(); 
			notifyObservers(null); 
		}
	}
	// Delegate to Observable
	public void addObserver(Observer observer)
	{
		_pipeObservable.addObserver(observer);
	}
	public void notifyObservers(Object arg)
	{
		_pipeObservable.notifyObservers(arg); 
	}
	public void setChanged()
	{
		_pipeObservable.setChanged(); 
	}
	protected PipeObservable getObservable()
	{
		return _pipeObservable; 
	}
}
