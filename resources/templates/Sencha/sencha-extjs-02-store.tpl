
// this is a Sencha ExtJS 'Store' class
// save this file as 'app/store/${classname}.js' 
// (name should be plural, like 'Stocks')

// TODO the store name should be plural (like 'Stocks', not 'Stock')
Ext.define('<<$APPLICATION_NAME>>.store.${classname}s', {
    extend: 'Ext.data.Store',

    requires: [
        '<<$APPLICATION_NAME>>.model.${classname}'
    ],

    model: '<<$APPLICATION_NAME>>.model.${classname}',

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

