document.addEventListener("DOMContentLoaded", function () {
    let today = new Date().toISOString().split("T")[0];
    let lastMonth = new Date();
    lastMonth.setDate(lastMonth.getDate() - 30);
    let lastMonthStr = lastMonth.toISOString().split("T")[0];

    document.getElementById("startDate").value = lastMonthStr;
    document.getElementById("endDate").value = today;
    document.getElementById("reportStartDate").innerText = formatDate(lastMonthStr);
    document.getElementById("reportEndDate").innerText = formatDate(today);
});

function updateReport() {
    let startDate = document.getElementById("startDate").value;
    let endDate = document.getElementById("endDate").value;

    if (startDate && endDate) {
        document.getElementById("reportStartDate").innerText = formatDate(startDate);
        document.getElementById("reportEndDate").innerText = formatDate(endDate);

        // TODO: Gọi API cập nhật báo cáo dựa trên thời gian đã chọn
    }
}

function formatDate(dateStr) {
    let date = new Date(dateStr);
    return date.toLocaleDateString("vi-VN");
}

