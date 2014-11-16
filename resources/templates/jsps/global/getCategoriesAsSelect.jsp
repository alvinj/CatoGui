
<%-- generate a list of categories as <OPTION> tags --%>
<%
  Vector currentCategories = (Vector)session.getValue("currentCategories");
  Category cat = new Category();
  Enumeration categoryEnumeration = currentCategories.elements();
  while ( categoryEnumeration.hasMoreElements() )
  {
    cat = (Category)categoryEnumeration.nextElement();
    String currCat = cat.getCategory();

  %> <option value="<%=currCat%>"><%=currCat%></option> <%
  }
  %>
