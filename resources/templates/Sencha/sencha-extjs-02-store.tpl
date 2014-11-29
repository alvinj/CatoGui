
<#import "/lib/includes.fm" as my>

// this is a Sencha ExtJS 'Store' class
// save this file as 'app/store/${classname}.js' 
// (name should be plural, like 'Stocks')

// TODO the store name should be plural (like 'Stocks', not 'Stock')
Ext.define('${my.applicationName}.store.${classnamePlural}', {
    extend: 'Ext.data.Store',

    requires: [
        '${my.applicationName}.model.${classname}'
    ],

    model: '${my.applicationName}.model.${classname}',

    proxy: {
        type: 'ajax',
        url: 'php/${objectname}.php',  //TODO your url here

        reader: {
            type: 'json'
        }
    },

    init: function(application) {
        console.log('${classname} Store created');
    }

});

