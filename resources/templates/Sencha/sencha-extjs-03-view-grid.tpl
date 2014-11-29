
// this is a Sencha ExtJS 'Grid Panel' class
// save this file as 'app/view/${classname}List.js'
// VERIFY: name should be singular, like 'StockList' or 'TransactionList'

<#import "/lib/includes.fm" as my>

Ext.define('${my.applicationName}.view.${classname}List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.${classname?lower_case}List',                    //VERIFY should be singular

    frame: true,
    store: Ext.create('${my.applicationName}.store.${classnamePlural}'),  //VERIFY - should be plural

    // valid column xtypes are:
    // none, 'numbercolumn', 'datecolumn'
    columns: [
    <#list fields as field>
        { 
            text: '${field.camelCaseFieldName?capitalize}', 
            width: 50,
            dataIndex: '${field.camelCaseFieldName}'
        }<#if field_has_next>,</#if>
    </#list>
],

    dockedItems: [
        {
            xtype: 'toolbar',
            flex: 1,
            dock: 'top',
            items: [
                {
                    xtype: 'button',
                    text: 'Add',
                    itemId: 'add',
                    iconCls: 'add'
                },
                {
                    xtype: 'button',
                    text: 'Delete',
                    itemId: 'delete',
                    iconCls: 'delete'
                }
            ]
        }
    ]

});


// -------------
// HELP/EXAMPLES
//--------------

        // integer column
        {
            text: 'Qty',
            width: 100,
            dataIndex: 'quantity',
            xtype: 'numbercolumn',
            format: '0,000',
            align: 'right'
        },

        // decimal/currency column
        {
            text: 'Price',
            width: 100,
            dataIndex: 'price',
            xtype: 'numbercolumn',
            format: '0.00',
            align: 'right'
        },

        // date
        {
            text: 'Date',
            width: 150,
            dataIndex: 'datetime',
            xtype: 'datecolumn',
            format: 'Y-m-d',
            align: 'right'
        },








