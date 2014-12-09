package com.alvinalexander.cato

import com.alvinalexander.inflector.Inflections

/**
 * Usage: Give the `main` method a string that represents a database table name,
 * something like `users` or `order_items`, and it will return a series of 
 * representations of that name to you.
 * 
 * For instance, if you supply the string `order_items`, this code will return
 * the following string:
 * 
 *     order_item:orderItem:OrderItem:orderItems:OrderItems
 *     
 * The theory is that you can process this string in your own shell scripts using
 * something like the Unix `cut` command.
 *
 * As a warning, if you supply a string that doesn't look like a normal,
 * lowercase, pluralized database table name, with multiple words separated by
 * underscores, I have no idea what this code will return. 
 */
object PlayFrameworkNames {
  
    // test table names: users, order_items

    def main(args: Array[String]) {
        val tableName = args(0).trim

        val singularName      = Inflections.singularize(tableName)         // order_item
        val camelSingularName = Inflections.camelize(singularName, false)  // orderItem
        val capSingularName   = Inflections.camelize(singularName, true)   // OrderItem
        val camelPluralName   = Inflections.camelize(tableName, false)     // orderItems
        val capPluralName     = Inflections.camelize(tableName, true)      // OrderItems

        println(s"${singularName}:${camelSingularName}:${capSingularName}:${camelPluralName}:${capPluralName}")
    }

}