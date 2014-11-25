package com.alvinalexander.annotations

import scala.annotation.meta._
 
/**
 * An annotation that designates that a method is an "impure" function,
 * i.e., that its result depends on something more than its input parameters,
 * or that it has one or more external side effects.
 */
class impure extends scala.annotation.StaticAnnotation