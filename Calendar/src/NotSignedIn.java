public class NotSignedIn extends State{
    public NotSignedIn(Model model) {
        super(model);
    }
    public void signIn(){
        model.setState(new SignedIn(model));
    }
    public void register(){
        //TO DO
    }
}
