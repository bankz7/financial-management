google.charts.load("current", {packages:["corechart","bar"]});
google.charts.setOnLoadCallback(drawChart);
function drawChart() {
    var arr = [['Category','balance']];
    expenses.forEach( (element) => {arr.push([element.category.categoryName, element.sum])} );
    var data = google.visualization.arrayToDataTable(arr);
    var options = {
        title: 'Monthly expenses by category',
        pieHole: 0.4,
        pieSliceText: 'label',
    };
    var chart = new google.visualization.PieChart(document.getElementById('expenses-pie-chart'));
    chart.draw(data, options);
}
google.charts.setOnLoadCallback(drawMonthlyChart);
function drawMonthlyChart() {
    var arr =  [['Month', 'Income', 'Expenses']];
    var map = {};
    monthlyData.forEach( (element) => {
        if(!map[element.month+'/'+element.year]){
            map[element.month+'/'+element.year] = {};
        }
        map[element.month+'/'+element.year][element.transactionType] = element.amount;
    } );

    for(var propertyName in map){
        arr.push([propertyName, map[propertyName]["INCOME"], map[propertyName]["EXPENSE"]]);
    }
    var data = google.visualization.arrayToDataTable(arr);
    var options = {
              chart: {
                title: 'Monthly data',
                subtitle: 'Income and Expense',
              },
              bars: 'vertical',
              vAxis: {format: 'decimal'},
              height: 200,
              chartArea:{
                width: 15
              },
              colors: ['#1b9e77', '#d95f02']
            };
    var chart = new google.charts.Bar(document.getElementById('monthly-chart'));
    chart.draw(data, options);
}