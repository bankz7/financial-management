google.charts.load("current", {packages:["corechart"]});
google.charts.setOnLoadCallback(drawChart);
function drawChart() {
    var arr = [['Account Name','balance']];
    message.forEach(m => (arr.push([m.accountName,m.balance])));
    var data = google.visualization.arrayToDataTable(arr);

    var options = {
        legend: 'none',
        pieSliceText: 'label',
    };
    var chart = new google.visualization.PieChart(document.getElementById('my_dataviz'));
    chart.draw(data, options);
}