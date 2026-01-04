package Model;

public class State {
    protected Model model;
    public State(Model model) {
        this.model = model;
    }
    public void exit(){
        model.exit();
    }
    public void setModel(Model model) {
        this.model = model;
    }
}
