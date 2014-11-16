package com.devdaily.web;

import java.util.Vector;

/**
 * Return a "paged" list of elements for a given dataset.
 * This is useful if you want to display items 1-20 on page one, items 21-40 on page two, etc.
 * @version 1.0
 * @author Al Alexander
 */

public final class PagedList
{

   /*  CLASS ATTRIBUTES  */
   private boolean hasNextPage;
   private int currentPage;
   private Vector list = new Vector();


   public PagedList ()
   {
     hasNextPage = false;
     currentPage = 0;
   }

   public void add (Object object)
   {
     list.addElement(object);
   }

   /**
    * @return A boolean that represents the hasNextPage.
    */
   public boolean hasNextPage ()
   {
      return hasNextPage;
   }

   /**
    * @return A int that represents the currentPage.
    */
   public int getCurrentPage ()
   {
      return currentPage;
   }

   /**
    * @return A Vector that represents the list.
    */
   public Vector getList ()
   {
      return list;
   }


   /**
    * @param hasNextPage boolean
    */
   public void setHasNextPage (boolean hasNextPage)
   {
      this.hasNextPage = hasNextPage;
   }

   /**
    * @param currentPage int
    */
   public void setCurrentPage (int currentPage)
   {
      this.currentPage = currentPage;
   }

   /**
    * @param list Vector
    */
   public void setList (Vector list)
   {
      this.list = list;
   }

}

