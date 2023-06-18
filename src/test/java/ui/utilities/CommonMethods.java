package ui.utilities;

import commonUtilities.ScenarioContext;

public class CommonMethods {

    public void putStepName(String stepName){
        ScenarioContext.put("stepName", stepName);
    }

    public void writeToReport(String message, String status){
        DriverFactory.writeToReport(message, status);
    }
}
