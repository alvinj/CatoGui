// this is a Sencha ExtJS 'Controller' class

//VERIFY - should be plural, like 'Stocks'
Ext.define('<<$APPLICATION_NAME>>.controller.${classname}', {
    extend: 'Ext.app.Controller',

    //VERIFY - should be singular and uc, like 'StockList'
    views: [
        '${classname}List',
    ],

    //VERIFY - should be plural and uc, like 'Stocks'
    stores: [
        '${classname}s'
    ],

    //VERIFY - both of these should be singular and lc, like 'stockList'
    refs: [
        {
            ref: '${objectname}List',
            selector: '${objectname}List'
        }
    ],

    init: function(application) {

        // add the components and events we listen to
        this.control({
            //VERIFY - these should all be singular and lc, like 'stockList'
            '${objectname}List': {
                render: this.onRender
            },
            '${objectname}List button#add': {
                click: this.onAdd${classname}ButtonClicked
            },
            '${objectname}List button#delete': {
                click: this.onDelete${classname}ButtonClicked
            },
            '${objectname}Form button#cancel': { //${classname}Form cancel button
                click: this.onAdd${classname}FormCancelClicked
            },
            '${objectname}Form button#save': { //${classname}Form save button
                click: this.onAdd${classname}FormSaveClicked
            }
        });

        // VERIFY - these should both be uc and plural
        if (!Ext.getStore('${classname}s')) {
            Ext.create('<<$APPLICATION_NAME>>.store.${classname}s');
        }    
    },

    /**
     * ${classname}Form events/methods
     */

    // the 'add' button click event
    onAdd${classname}ButtonClicked: function(button, event, options) {
        var win = Ext.create('<<$APPLICATION_NAME>>.view.${classname}Form');
        win.setTitle('Add ${classname}');
        win.show();
    },

    // the 'cancel' button click event on the 'add form'
    onAdd${classname}FormCancelClicked: function(button, event, options) {
        button.up('window').close();
    },

    // the 'save' button click event on the 'add form'
    onAdd${classname}FormSaveClicked: function(button, event, options) {
        var win = button.up('window'),
            formPanel = win.down('form'),
            store = this.get${classname}List().getStore();

        if (formPanel.getForm().isValid()) {
            formPanel.getForm().submit({
                clientValidation: true,
                // TODO - PUT YOUR URL HERE
                url: 'php/${objectname}save.php',
                success: function(form, action) {
                    var result = action.result;
                    console.log(result);
                    if (result.success) {
                        Packt.util.Alert.msg('Success!', '${classname} was saved.');
                        store.load();
                        win.close();                      
                    } else {
                        Packt.util.Util.showErrorMsg(result.msg);
                    }
                },
                failure: function(form, action) {
                    switch (action.failureType) {
                        case Ext.form.action.Action.CLIENT_INVALID:
                            Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
                            break;
                        case Ext.form.action.Action.CONNECT_FAILURE:
                            Ext.Msg.alert('Failure', 'Ajax communication failed');
                            break;
                        case Ext.form.action.Action.SERVER_INVALID:
                            Ext.Msg.alert('Failure', action.result.msg);
                   }
                }
            });
        }
    },

    // delete one or more ${classname}s
    onDelete${classname}ButtonClicked: function (button, e, options) {
        var grid = this.get${classname}List(),
            record = grid.getSelectionModel().getSelection(), 
            store = grid.getStore();

        if (store.getCount() >= 1 && record[0]) {
            var idToDelete = record[0].get('id');
            Ext.Msg.show({
                 title:'Delete?',
                 msg: 'Are you sure you want to delete ID(' + idToDelete + ')?',
                 buttons: Ext.Msg.YESNO,
                 icon: Ext.Msg.QUESTION,
                 fn: function (buttonId){
                    if (buttonId == 'yes'){
                        Ext.Ajax.request({
                            // TODO - YOUR 'DELETE' URL HERE
                            url: 'php/delete${classname}.php',
                            params: {
                                id: idToDelete
                            },
                            success: function(conn, response, options, eOpts) {
                                var result = Packt.util.Util.decodeJSON(conn.responseText);
                                if (result.success) {
                                    Packt.util.Alert.msg('Success', 'The ${objectname} was deleted.');
                                    store.load();
                                } else {
                                    Packt.util.Util.showErrorMsg(conn.responseText);
                                }
                            },
                            failure: function(conn, response, options, eOpts) {
                                Packt.util.Util.showErrorMsg(conn.responseText);
                            }
                        });
                    }
                 }
            });
        } else {
            Ext.Msg.show({
                title: 'Data Selection',
                msg: 'Please select at least one ${objectname}.',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        }
    },

    onRender: function(component, options) {
        component.getStore().load();
    }

});











