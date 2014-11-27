
<#import "/lib/includes.fm" as my>

// this is a Sencha ExtJS 'form'
// this is a form to let the user add and edit a ${classname}

// save this file as 'app/view/${classname}Form.js'
// VERIFY - should be capitalized and singular, like 'StockForm'
Ext.define('${my.applicationName}.view.${classname}Form', {
    extend: 'Ext.window.Window',

    // VERIFY - should be lc and singular, like 'stockForm'
    alias: 'widget.${objectname}Form',

    height: 300,
    width: 360,

    layout: {
        align: 'stretch',
        type: 'vbox'
    },

    title: 'Add/Edit ${classname}',

    items: [
        {
            xtype: 'form',
            bodyPadding: 5,
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            defaults: {
                anchor: '100%',
                xtype: 'textfield',
                blankText: 'Required',
                labelWidth: 90
            },
            items: [
                // TODO do i need this hidden field?
                // TODO the 'id' field is duplicated here; probably want it hidden
                {
                    xtype: 'hiddenfield',
                    fieldLabel: 'Label',
                    name: 'id'
                },
                <#list fields as field>
                {
                    fieldLabel: '${field.camelCaseFieldName?capitalize}',
                    name: '${field.camelCaseFieldName}',
                    itemId: '${field.camelCaseFieldName}',
                    allowBlank: false,
                    maxLength: 10
                }<#if field_has_next>,</#if>
                </#list>
            ]
        }
    ],

    dockedItems: [
        {
            xtype: 'toolbar',
            flex: 1,
            dock: 'bottom',
            ui: 'footer',
            layout: {
                pack: 'end',
                type: 'hbox'
            },
            items: [
                {
                    xtype: 'button',
                    text: 'Cancel',
                    itemId: 'cancel',
                    iconCls: 'cancel'
                },
                {
                    xtype: 'button',
                    text: 'Save',
                    itemId: 'save',
                    iconCls: 'save'
                }
            ]
        }
    ]

});



// -------------
// HELP/EXAMPLES
// -------------

                // RADIO GROUP
                // -----------
                {
                    fieldLabel: 'Type',
                    itemId: 'ttype',
                    xtype: 'radiogroup',
                    // horizontal: true,   //this works, but separates the buttons too much
                    layout: 'hbox',
                    // defaults: {     
                    //     flex: 1
                    // },
                    items: [{
                        boxLabel: 'Buy',
                        inputValue: 'B',
                        name: 'ttype',
                        checked: true,
                        flex: 1
                    }, {
                        boxLabel: 'Sell',
                        inputValue: 'S',
                        name: 'ttype',
                        flex: 4
                    }]
                },

                // INT FIELD with REGEX
                // --------------------
                {
                    fieldLabel: 'Qty',
                    name: 'quantity',
                    itemId: 'quantity',
                    allowBlank: false,
                    maxLength: 10,
                    maskRe: /([0-9]+)$/   // int field
                },

                // HIDDEN FIELD
                // ------------
                {
                    xtype: 'hiddenfield',
                    fieldLabel: 'datetime',
                    name: 'datetime'
                },


















