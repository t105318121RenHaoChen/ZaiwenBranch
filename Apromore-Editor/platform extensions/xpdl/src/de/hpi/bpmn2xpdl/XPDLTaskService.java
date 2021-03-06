package de.hpi.bpmn2xpdl;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmappr.Attribute;
import org.xmappr.RootElement;

@RootElement("TaskService")
public class XPDLTaskService extends XMLConvertible {

    @Attribute("Implementation")
    protected String implementation;

    public String getImplementation() {
        return implementation;
    }

    public void readJSONimplementation(JSONObject modelElement) {
        setImplementation(modelElement.optString("implementation"));
    }

    public void readJSONtaskref(JSONObject modelElement) {
    }

    public void readJSONtasktypeunknowns(JSONObject modelElement) {
        readUnknowns(modelElement, "tasktypeunknowns");
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public void writeJSONimplementation(JSONObject modelElement) throws JSONException {
        modelElement.put("implementation", getImplementation());
    }

    public void writeJSONtasktype(JSONObject modelElement) throws JSONException {
        modelElement.put("tasktype", "Service");
    }

    public void writeJSONtasktypeunknowns(JSONObject modelElement) throws JSONException {
        writeUnknowns(modelElement, "tasktypeunknowns");
    }
}
