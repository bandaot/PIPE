package pipe.actions;

import pipe.actions.file.FileAction;
import pipe.models.PipeApplicationModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: st809
 * Date: 25/10/2013
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public enum ActionEnum {
    CREATE, OPEN, CLOSE, SAVE, SAVEAS, PRINT, EXPORTPNG,
    EXPORTTN, EXPORTPS, IMPORT, EXIT, UNDO, REDO, CUT,
    COPY, PASTE, DELETE, SELECT, PLACE, TRANSACTION, TIMED_TRANSACTION, ARC, INHIBITOR_ARC, ANNOTATION, TOKEN,
    DRAG, RATE_PARAMETER, TOGGLE_GRID, ZOOM_OUT, ZOOM_IN, START, STEP_BACK, STEP_FORWARD, RANDOM, ANIMATE, DELETE_TOKEN, SPECIFY_TOKEN, GROUP_TRANSITIONS, UNFOLD, UNGROUP_TRANSITIONS, CHOOSE_TOKEN_CLASS;

}