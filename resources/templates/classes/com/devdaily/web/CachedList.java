package com.devdaily.web;

import java.util.Vector;
import java.util.Enumeration;

/**
 * Return a "cached" list of elements for a given dataset.
 * This is useful if you want to display items 1-20 on page one, items 21-40 on page two, etc.
 * @version 1.0
 * @author Al Alexander
 */

public final class CachedList
{

   /*  CLASS ATTRIBUTES  */
   private boolean hasNextPage;
   private int currentPageNumber;
   private Vector list = new Vector();  // the entire list
   private int numItemsPerPage = 10;    // the number of elements per page
   private int numPagesInList;


   public CachedList ()
   {
     hasNextPage = false;
     currentPageNumber = 0;
   }

   /**
    Create a CachedList from a given Vector.
    @param The Vector that the CachedList should be created from.
    */
   public CachedList (Vector v)
   {
     this();
     list = v;
     calculateNumPagesInList();
   }

   /**
    @return The number of pages in the current list. Calculated from the
    list size and number of items per page.
    */
   public int numPagesInList ()
   {
      return numPagesInList;
   }

   /**
    Determine the number of pages from the current list size and num items
    per page.
    */
   private void calculateNumPagesInList ()
   {
     if ( (list.size() % numItemsPerPage) != 0 )
     {
       numPagesInList = (int) ( (list.size() / numItemsPerPage) + 1 );
     }
     else
     {
       numPagesInList = (int) ( list.size() / numItemsPerPage );
     }
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
   public int getCurrentPageNumber ()
   {
      return currentPageNumber;
   }

   /**
    * @return A Vector that represents the entire list.
    */
   public Vector getEntireList ()
   {
      return list;
   }

   /**
    @return A Vector that represents the CachedList for the given page number.
    Precondition: our "list" should already be created.
    */
   public Vector getListForPage (int desiredPageNumber)
   {
     int firstElement;
     int lastElement;
     Vector itemsForPage = new Vector(numItemsPerPage);
     hasNextPage = false;

     // determine the values of the first and last elements we should return
     firstElement = (desiredPageNumber-1)*numItemsPerPage + 1;
     lastElement  = firstElement + numItemsPerPage - 1;

     // set our currentPageNumber to desiredPageNumber.
     // if we've been given a bogus page number, return null.
     if ( firstElement <= list.size() )
     {
       currentPageNumber = desiredPageNumber;
     }
     else
     {
       return null;
     }

     // assumes "list" is already populated
     Enumeration e = list.elements();

     Object o = null;
     int counter = 0;
     while ( e.hasMoreElements() )
     {
       o = e.nextElement();
       counter++;
       if ( counter > lastElement )
       {
         hasNextPage = true;
         break;
       }
       if ( counter >= firstElement  &&  counter <= lastElement )
       {
         itemsForPage.addElement(o);
       }
     }

     return itemsForPage;
   }


   /**
    Add an Object to the existing list.
    */
   public void add (Object object)
   {
     list.addElement(object);
     calculateNumPagesInList();
   }

   /**
    * @param hasNextPage boolean
    */
   public void setHasNextPage (boolean hasNextPage)
   {
     this.hasNextPage = hasNextPage;
   }

   /**
    * @param currentPageNumber int
    */
   public void setCurrentPageNumber (int currentPageNumber)
   {
     this.currentPageNumber = currentPageNumber;
   }

   /**
    * @param list Vector
    */
   public void setList (Vector list)
   {
     this.list = list;
     calculateNumPagesInList();
   }

}

