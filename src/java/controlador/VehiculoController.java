package controlador;

import modelo.Vehiculo;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import bean.VehiculoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("vehiculoController")
@SessionScoped
public class VehiculoController implements Serializable {

    @EJB
    private bean.VehiculoFacade ejbFacade;
    private List<Vehiculo> items = null;
    private Vehiculo selected;

    public VehiculoController() {
    }

    public Vehiculo getSelected() {
        return selected;
    }

    public void setSelected(Vehiculo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setVehiculoPK(new modelo.VehiculoPK());
    }

    private VehiculoFacade getFacade() {
        return ejbFacade;
    }

    public Vehiculo prepareCreate() {
        selected = new Vehiculo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VehiculoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VehiculoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VehiculoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Vehiculo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Vehiculo getVehiculo(modelo.VehiculoPK id) {
        return getFacade().find(id);
    }

    public List<Vehiculo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Vehiculo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Vehiculo.class)
    public static class VehiculoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VehiculoController controller = (VehiculoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "vehiculoController");
            return controller.getVehiculo(getKey(value));
        }

        modelo.VehiculoPK getKey(String value) {
            modelo.VehiculoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new modelo.VehiculoPK();
            key.setAño(Integer.parseInt(values[0]));
            key.setPlaca(values[1]);
            return key;
        }

        String getStringKey(modelo.VehiculoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getAño());
            sb.append(SEPARATOR);
            sb.append(value.getPlaca());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Vehiculo) {
                Vehiculo o = (Vehiculo) object;
                return getStringKey(o.getVehiculoPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Vehiculo.class.getName()});
                return null;
            }
        }

    }

}
