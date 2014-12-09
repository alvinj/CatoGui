#!/bin/bash

# ------------------------------------------------------------------------------------------
# Use this script to build Play Framework directories and files based off of a given
# database table name. For example, given the table name "users", this script will create:
#
#     app/views/user/list.scala.html
#     app/views/user/form.scala.html
#     app/models/User.scala
#     app/controllers/Users.scala
#
#  It will also append to:
#
#     conf/routes
#
# It's assumed that the app/controllers, app/views, and conf directories already exist.
# This script will (silently) attempt to create the other directories.
#
# ------------------------------------------------------------------------------------------


# -------------------------------(  start config section  )---------------------------------
# cato
CATO_DIR="/Users/al/Projects/Scala/CatoGui"
RESOURCES_DIR="$CATO_DIR/resources"
CATO_JAR_FILE="$CATO_DIR/target/scala-2.10/CatoGui-assembly-1.0.jar"
FULL_JDBC_CLASSPATH="$RESOURCES_DIR/mysql-connector-java-5.1.34-bin.jar:$CATO_JAR_FILE"
MAPPING_FILE="$RESOURCES_DIR/datatypemappings.json"

# templates
TEMPLATES_DIR="$RESOURCES_DIR/templates/PlayFrameworkFast"
ROUTES_TEMPLATE="$TEMPLATES_DIR/routes.tpl"
CONTROLLER_TEMPLATE="$TEMPLATES_DIR/controller.tpl"
MODEL_TEMPLATE="$TEMPLATES_DIR/model.tpl"
LIST_VIEW_TEMPLATE="$TEMPLATES_DIR/list.scala.html.tpl"
FORM_VIEW_TEMPLATE="$TEMPLATES_DIR/form.scala.html.tpl"

# database
DB_USER=root
DB_PASS=root
DB_DRIVER=com.mysql.jdbc.Driver
DB_URL=jdbc:mysql://localhost:8889/pim

# --------------------------------(  end config section  )----------------------------------


if [ -z "$1" ]
then
  echo ""
  echo "Usage: You need to supply the name of one database table name"
  echo "       to this script, something like 'order_items'"
  exit -1
fi

DB_TABLE_NAME=$1

declare -r TRUE=0
declare -r FALSE=1

# ----------------------------------------------------------------
# takes a string and returns `true` if it seems to represent "yes"
# ----------------------------------------------------------------
function isYes() {
  local x=$1
  [ $x = "y" ] && echo $TRUE; return
  [ $x = "Y" ] && echo $TRUE; return
  [ $x = "yes" ] && echo $TRUE; return
  echo $FALSE
}

# -----------------------------------------
# this function generates the desired code.
# it writes its output to STDOUT.
# -----------------------------------------
# params are: templateFile
# -----------------------------------------
function gen_code() {
  local templateFile=$1
  java -classpath "$FULL_JDBC_CLASSPATH"   \
     com.alvinalexander.cato.CatoCmdLine   \
     --mappingfile  $MAPPING_FILE          \
     --templatefile $templateFile          \
     --user         $DB_USER               \
     --password     $DB_PASS               \
     --driver       $DB_DRIVER             \
     --url          $DB_URL                \
     --table        $DB_TABLE_NAME
}


# STEP 1: get a string that looks like this: "order_item:orderItem:OrderItem:orderItems:OrderItems"
# -------------------------------------------------------------------------------------------------
names=`java -classpath "$CATO_JAR_FILE" com.alvinalexander.cato.PlayFrameworkNames $DB_TABLE_NAME`

singular_underscore_name=`echo $names  | cut -d: -f1`
singular_camelcase_name=`echo $names   | cut -d: -f2`
singular_capitalized_name=`echo $names | cut -d: -f3`
plural_camelcase_name=`echo $names     | cut -d: -f4`
plural_capitalized_name=`echo $names   | cut -d: -f5`

# TODO: show these results to the user, ask if they want to continue
# TODO: check and make sure it looks like we're in the root of a Play project

VIEWS_DIR="app/views/${singular_underscore_name}"
MODELS_DIR="app/models"

# -----------------------
# STEP 2: prompt the user
# -----------------------

ROUTES_FILE="conf/routes"
MODEL_FILE="${MODELS_DIR}/${singular_capitalized_name}.scala"
CONTROLLER_FILE="app/controllers/${plural_capitalized_name}.scala"
LIST_VIEW_FILE="${VIEWS_DIR}/list.scala.html"
FORM_VIEW_FILE="${VIEWS_DIR}/form.scala.html"

echo ""
echo "About to do these things:"
echo "-------------------------------------------"
echo "Create:    $MODELS_DIR"
echo "Create:    $VIEWS_DIR"
echo "Append to: $ROUTES_FILE"
echo "Overwrite: $MODEL_FILE"
echo "Overwrite: $CONTROLLER_FILE"
echo "Overwrite: $LIST_VIEW_FILE"
echo "Overwrite: $FORM_VIEW_FILE"
echo "-------------------------------------------"
echo ""
read -p "Proceed? (y/N): " proceed
proceed=${proceed:-n}

if [ "$(isYes $proceed)" = "$TRUE" ]; then

    mkdir -p $VIEWS_DIR  2> /dev/null
    mkdir -p $MODELS_DIR 2> /dev/null

    echo "appending to routes file ..."
    gen_code $ROUTES_TEMPLATE     >> $ROUTES_FILE

    echo "generating controller ..."
    gen_code $CONTROLLER_TEMPLATE  > $CONTROLLER_FILE

    echo "generating model ..."
    gen_code $MODEL_TEMPLATE       > $MODEL_FILE

    echo "generating list view ..."
    gen_code $LIST_VIEW_TEMPLATE   > $LIST_VIEW_FILE

    echo "generating add/edit form ..."
    gen_code $FORM_VIEW_TEMPLATE   > $FORM_VIEW_FILE

    echo ""
    echo "I hope that worked!"
    echo ""
else
    echo ""
    echo "okay, nothing was done"
    echo ""
fi



