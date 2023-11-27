package view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {
    private String currentView;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void setActiveView(String viewName) {
        String oldView = this.currentView;
        this.currentView = viewName;
        propertyChangeSupport.firePropertyChange("view", oldView, viewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
