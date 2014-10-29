package net.skybert.moccasin.model;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * IndianNameValidator which validates tha the indian names don't sound
 * un-indian.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@FacesValidator(value = "indianNameValidator")
public class IndianNameValidator implements Validator
{

  @Override
  public void validate(FacesContext context, UIComponent component, Object value)
      throws ValidatorException
  {

    String enteredString = value.toString();

    if (enteredString.endsWith("sen") || enteredString.endsWith("son"))
    {
      FacesMessage msg = new FacesMessage("The name " + enteredString
          + " is not a true indian name, ugh!");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ValidatorException(msg);
    }

  }

}
