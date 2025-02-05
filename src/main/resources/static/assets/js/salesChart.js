document.addEventListener("DOMContentLoaded", function () {
    // Lấy ngày mặc định từ input
    let startDate = document.getElementById("startDate").value;
    let endDate = document.getElementById("endDate").value;

    // Hiển thị ngày mặc định trên báo cáo
    document.getElementById("reportStartDate").innerText = formatDate(startDate);
    document.getElementById("reportEndDate").innerText = formatDate(endDate);

    // Cập nhật biểu đồ với dữ liệu từ khoảng ngày mặc định
    updateChart(startDate, endDate);
});

function updateReport() {
    let startDate = document.getElementById("startDate").value;
    let endDate = document.getElementById("endDate").value;

    // Cập nhật text hiển thị ngày
    document.getElementById("reportStartDate").innerText = formatDate(startDate);
    document.getElementById("reportEndDate").innerText = formatDate(endDate);

    // Cập nhật dữ liệu cho biểu đồ
    updateChart(startDate, endDate);
}

function updateChart(startDate, endDate) {
    // Lấy dữ liệu từ server hoặc xử lý dữ liệu nội bộ
    console.log("Cập nhật biểu đồ từ ngày:", startDate, "đến ngày:", endDate);

    // Cập nhật dữ liệu cho biểu đồ Sales Chart (giả định bạn đang dùng Chart.js)
    let salesChart = document.getElementById("salesChart").getContext("2d");

    // Xóa biểu đồ cũ nếu có
    if (window.mySalesChart) {
        window.mySalesChart.destroy();
    }

    // Tạo biểu đồ mới với ngày từ bộ lọc
    window.mySalesChart = new Chart(salesChart, {
        type: "line",
        data: {
            labels: generateDateLabels(startDate, endDate), // Tạo danh sách ngày
            datasets: [{
                label: "Doanh thu từng ngày",
                data: generateFakeData(startDate, endDate), // Tạo dữ liệu giả định
                borderColor: "rgba(75, 192, 192, 1)",
                borderWidth: 2,
                fill: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

function formatDate(dateStr) {
    let date = new Date(dateStr);
    return date.toLocaleDateString("vi-VN");
}

function generateDateLabels(startDate, endDate) {
    let start = new Date(startDate);
    let end = new Date(endDate);
    let labels = [];

    while (start <= end) {
        labels.push(start.toLocaleDateString("vi-VN"));
        start.setDate(start.getDate() + 1);
    }
    return labels;
}

function generateFakeData(startDate, endDate) {
    let start = new Date(startDate);
    let end = new Date(endDate);
    let data = [];

    while (start <= end) {
        data.push(Math.floor(Math.random() * 100)); // Tạo số ngẫu nhiên
        start.setDate(start.getDate() + 1);
    }
    return data;
}
