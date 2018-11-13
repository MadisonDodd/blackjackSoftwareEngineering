package clientCommunication;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import clientUI.CreateAccountPanel;

public class CreateAccountControl implements ActionListener
{
	  private JPanel container;
	  private GameClient client;

	  public CreateAccountControl(JPanel container, GameClient client)
	  {
	    this.container = container;
	    this.client = client;
	    
	    try
		{
			client.openConnection();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	  }
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 // Get the name of the button clicked.
	    String command = e.getActionCommand();

	    // The Cancel button takes the user back to the initial panel.
	    if (command == "Cancel")
	    {
	      CardLayout cardLayout = (CardLayout)container.getLayout();
	      cardLayout.show(container, "1");
	    }
	    else if (command == "Submit")
	    {
	    	CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
	    	CreateAccountData data = new CreateAccountData(createAccountPanel.getUsername(), createAccountPanel.getPassword(), createAccountPanel.getVerifyPassword());
	    	if (data.getUsername().equals("") || data.getPassword().equals(""))
	    	{
	            displayError("You must enter a username and password.");
	            return;
	    	}
	    	else if (!data.getPassword().equals(data.getVerifyPassword())) 
	    	{
	    		displayError("Passwords do not match");
	    		return;
	    	}
	    	else if (data.getPassword().length() < 6) {
	    		displayError("Password must be at least 6 characters");
	    		return;
	    	}
	    	
	    	
	    	try
			{
				client.sendToServer(data);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	    }
	}
	public void displayError(String error)
	{
		// TODO Auto-generated method stub
	    CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
	    createAccountPanel.setError(error);
	}
	
	public void createAccountSuccess() 
	{
        CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container,  "4");
	}
	
	

}
