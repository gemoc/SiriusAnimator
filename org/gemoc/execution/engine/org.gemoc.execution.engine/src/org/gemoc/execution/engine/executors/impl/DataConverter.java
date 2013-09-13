/**
 * 
 */
package org.gemoc.execution.engine.executors.impl;

import org.gemoc.execution.engine.Activator;
import org.gemoc.execution.engine.feedback.data.FeedbackData;
import org.gemoc.execution.engine.feedback.data.impl.easy.ObjectFeedbackData;

/**
 * @author flatombe
 * 
 */
public class DataConverter {

    public static FeedbackData convertToFeedbackData(Object o) {
        FeedbackData feedback = null;

        try {
            String s = (String) o;
            feedback = new ObjectFeedbackData((Object) s);
        } catch (ClassCastException e) {
            try {
                Boolean b = (Boolean) o;
                feedback = new ObjectFeedbackData((Object) b);
            } catch (ClassCastException e1) {
                String errorMessage = "Cannot comprehend the return type fo the dsa.";
                Activator.getMessagingSystem().error(errorMessage, Activator.PLUGIN_ID);
                Activator.error(errorMessage, e1);
            }
        }

        return feedback;
    }

}
