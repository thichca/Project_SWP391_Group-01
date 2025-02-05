function toggleFormAdd() {
    const form = document.getElementById("addCustomerForm");
    form.style.display = (form.style.display === "none") ? "block" : "none";
}

function toggleFormEdit(id, name, phone, address, debtBalance) {
    const form = document.getElementById("editCustomerForm");
    form.style.display = "block";

    document.getElementById("editId").value = id;
    document.getElementById("editName").value = name;
    document.getElementById("editPhone").value = phone;
    document.getElementById("editAddress").value = address;
    document.getElementById("editDebtBalance").value = debtBalance;
}

function closeEditForm() {
    document.getElementById("editCustomerForm").style.display = "none";
}
