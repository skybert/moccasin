package net.skybert.moccasin.ws.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.skybert.moccasin.data.Indian;

/**
 * IndianWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@WebService(targetNamespace = "http://skybert.net/moccasin"

)
public class IndianWS
{

  @WebMethod
  public @WebResult(name = "indian")
  Indian getIndian(@WebParam(name = "id") Integer pId)
  {
    return new Indian();
  }
}
