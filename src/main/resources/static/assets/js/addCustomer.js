function toggleFormAdd() {
    const form = document.getElementById("addCustomerForm");
    form.style.display = (form.style.display === "none") ? "block" : "none";
}

function toggleFormEdit(id, name, phone, address, debtBalance, note) {
    const form = document.getElementById("editCustomerForm");
    form.style.display = "block";

    document.getElementById("editId").value = id;
    document.getElementById("editName").value = name;
    document.getElementById("editPhone").value = phone;
    document.getElementById("editAddress").value = address;
    document.getElementById("editDebtBalance").value = debtBalance;
    document.getElementById("editNote").value = note;
}

function closeEditForm() {
    document.getElementById("editCustomerForm").style.display = "none";
}

