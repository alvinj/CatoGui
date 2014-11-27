<#import "/lib/includes.fm" as my>

// this is a Sencha ExtJS 'Model' class

// save this file as 'app/model/${classname}.js'
// (name should be singular, like 'Stock' or 'Transaction')

// (A) USE THIS BLOCK IF YOU WANT TYPES
// ------------------------------------
Ext.define('${my.applicationName}.model.${classname}', {
    extend: 'Ext.data.Model',

    idProperty: 'id',

    fields: [
    <#list fields as field>
        { name: '${field.camelCaseFieldName}', type: '${field.senchaFieldType}' },
    </#list>
    ]

});


// (B) USE THIS BLOCK IF YOU DON'T WANT TYPES
// ------------------------------------------
Ext.define('<<$APPLICATION_NAME>>.model.${classname}', {
    extend: 'Ext.data.Model',

    idProperty: 'id',

    fields: [
    <#list fields as field>
        { name: '${field.camelCaseFieldName}'},
    </#list>
    ]

});



