package net.skybert.moccasin.ws;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logout
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@WebServlet("/logout")
public class Logout extends HttpServlet
{
  private static final long serialVersionUID = 4580412459060905811L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException
  {
    request.getSession().invalidate();
    response.sendRedirect(request.getContextPath());
  }
}
