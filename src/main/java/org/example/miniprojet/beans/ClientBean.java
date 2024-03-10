package org.example.miniprojet.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import org.example.miniprojet.models.Client;
import org.example.miniprojet.services.ClientDAO;


import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@ManagedBean(name = "client")
@ViewScoped
public class ClientBean {
    private ResourceBundle messages;
    private ClientDAO clientDAO = null;
    private boolean showAddForm = false;
    private Client newClient;
    private List<Client> clients;

    private String keyword;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void filterByKeyword(){
        clients = clientDAO.findByKeyword(this.keyword);
    }

    public ClientBean(){
        this.initializeResourcesBundle();
    }

    private void initializeResourcesBundle(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String localeString = (String) session.getAttribute("locale");
        Locale locale;
        if (localeString != null) {
            locale = new Locale(localeString);
        } else {
            locale = new Locale("en");
        }
        messages = ResourceBundle.getBundle("messages", locale);
    }

    public void changeLocale(String localeCode) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("locale", localeCode);
        facesContext.getViewRoot().setLocale(new Locale(localeCode));
        this.initializeResourcesBundle();
    }

    public Client getNewClient() {
        return newClient;
    }

    public void setNewClient(Client newClient) {
        this.newClient = newClient;
    }




    public List<Client> getClients(){
        return this.clients;
    }

    @PostConstruct
    public void init() {
        clientDAO = new ClientDAO();
        clients = clientDAO.selectAll();
    }
    public void showAddForm(){
        this.showAddForm = true;
        this.newClient = new Client();
    }

    public boolean getShowAddForm(){
        return this.showAddForm;
    }

    public void edit(Client c){
        if(clientDAO.update(c)){
            this.toggleEditable(c);
            addMessage(this.__("global.success"),this.__("message.client_updated_successfully"), FacesMessage.SEVERITY_INFO);
        }else{
            addMessage(this.__("global.error"),this.__("message.client_updating_error"), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void delete(Client c){
        if(clientDAO.delete(c.getId())){
            clients.remove(c);
            addMessage(this.__("global.success"),this.__("message.client_deleted_successfully"), FacesMessage.SEVERITY_INFO);
        }else{
            addMessage(this.__("global.error"),this.__("message.client_deleting_error"), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void insert(){
        if(clientDAO.insert(this.newClient)){
            clients = clientDAO.selectAll();
            this.toggleShowAddForm();
            addMessage(this.__("global.success"),this.__("message.client_inserted_successfully"), FacesMessage.SEVERITY_INFO);
        }else{
            addMessage(this.__("global.error"),this.__("message.client_inserting_error"), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void toggleEditable(Client c){
        for (Client client : clients){
            if(client.getId() == c.getId()){
                client.setEditable(!client.getEditable());
            }
        }
    }

    public void toggleShowAddForm(){
        this.showAddForm = !this.showAddForm;
        this.newClient = new Client();
    }

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage("formManagedBean", message);
    }

    public String __(String key){
        return messages.getString(key);
    }


}
