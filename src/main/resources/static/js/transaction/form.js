$(document).ready(function(){
    updateTxType();
    updateExchangeRate();
    $('#transactionT').change(updateTxType);
    $('#fromAcc, #toAcc, #transactionT').change(updateExchangeRate);
    function updateFromAcc(){
        var fromAccIndex = $('#fromAcc option:selected').index();
        var toAccIndex = $('#toAcc option:selected').index();
        if(fromAccIndex == toAccIndex){
            if(toAccIndex == 0){
                $('#fromAcc').prop("selectedIndex", 1);
            }else{
                $('#fromAcc').prop("selectedIndex", 0);
            }
        }
    }
   function updateToAcc(){
        var fromAccIndex = $('#fromAcc option:selected').index();
        var toAccIndex = $('#toAcc option:selected').index();
        if(fromAccIndex == toAccIndex){
            if(fromAccIndex == 0){
                $('#toAcc').prop("selectedIndex", 1);
            }else{
                $('#toAcc').prop("selectedIndex", 0);
            }
        }
    }
    $('#fromAcc').change(updateToAcc);
    $('#toAcc').change(updateFromAcc);

    function updateTxType(){
        if($('#transactionT').val() == 'TRANSFER'){
            $('#div-cate').hide();
            $('#div-to-acc').show();
            $("#label-form-acc").text("From Account");
            updateToAcc();
            updateExchangeRate();
        }else{
            $('#div-cate').show();
            $('#div-to-acc').hide();
            $("#label-form-acc").text("Account");
        }
    };
    function updateExchangeRate(){
        console.log($('#fromAcc').val());
        if($('#transactionT').val() == 'TRANSFER'){
            var fromAccIndex = $('#fromAcc option:selected').index();
            var fromAccCurrencySymbol = accountList[fromAccIndex].currency.symbol;
            var toAccIndex = $('#toAcc option:selected').index();
            var toAccCurrencySymbol = accountList[toAccIndex].currency.symbol;
            if(fromAccCurrencySymbol != toAccCurrencySymbol){
                $('#exchange_rate_label').text("Exchange rate : "+fromAccCurrencySymbol+"->"+toAccCurrencySymbol);
                $('#div-exchange-rate').show();
            }else{
                 $('#div-exchange-rate').hide();
             }
        }else{
            $('#div-exchange-rate').hide();
        }
    }
});