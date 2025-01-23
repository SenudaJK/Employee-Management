getAllEmployees();
function saveEmployee() {
    let firstName = htmlspecialchars($('#firstname').val());
    let lastName = htmlspecialchars($('#lastname').val());
    let email = htmlspecialchars($('#email').val());
    let telNo = htmlspecialchars($('#telno').val());

    if (!firstName) {
        alert("First Name is required.");
        return;
    }
    if (!lastName) {
        alert("Last Name is required.");
        return;
    }
    if (!email) {
        alert("Email is required.");
        return;
    }
    if (!telNo) {
        alert("Telephone Number is required.");
        return;
    }

    if (isFormEmpty(firstName, lastName, email, telNo)) {
        alert("Empty Form! Please fill in all fields.");
        return;
    }

    if (!validateInputs(firstName, lastName, email, telNo)) {
        return;
    }

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8082/api/employees",
        async: true,
        data: JSON.stringify({
            "firstName": firstName,
            "lastName": lastName,
            "email": email,
            "telephone": telNo
        }),
        success: function (data) {
            alert("saved");
            window.location.href = 'view.html';
        },
        error: function (xhr, exception) {
            alert("Error");
        }
    });
}

function deleteEmployee(id) {
    $.ajax({
        method: "DELETE",
        url: "http://localhost:8082/api/employees/" + id,
        async: true,
        success: function (data) {
            alert("Deleted");
            getAllEmployees();
        },
        error: function (xhr, exception) {
            alert("Error");
        }
    });
}

function getAllEmployees() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8082/api/employees",
        async: true,
        success: function (data) {
            console.log("Response Data:", data);
            $('#empTable').empty();
            let searchInput = $('#searchInput').val() ? $('#searchInput').val().toLowerCase() : '';

            for (let emp of data) {
                let empID = emp.id;
                let firstName = typeof emp.firstName === "string" ? emp.firstName.toLowerCase() : '';
                let lastName = typeof emp.lastName === "string" ? emp.lastName.toLowerCase() : '';
                let email = typeof emp.email === "string" ? emp.email.toLowerCase() : '';
                let telNo = emp.telephone || '';

                if (firstName.includes(searchInput) || lastName.includes(searchInput) || email.includes(searchInput)) {
                    var row = `<tr>
                        <td>${empID}</td>
                        <td>${emp.firstName || ''}</td>
                        <td>${emp.lastName || ''}</td>
                        <td>${emp.email || ''}</td>
                        <td>${telNo}</td>
                        <td>
                            <button class="btn btn-success" onclick="editEmployee(${empID})">Edit</button>
                            <button class="btn btn-danger" onclick="deleteEmployee(${empID})">Delete</button>
                        </td>
                    </tr>`;
                    $('#empTable').append(row);
                }
            }
        },
        error: function (xhr, exception) {
            console.error("Error:", xhr.responseText);
            alert("Error: " + xhr.responseText);
        }
    });
}


function editEmployee(id) {
    $.ajax({
        method: "GET",
        url: "http://localhost:8082/api/employees/" + id,
        async: true,
        success: function (data) {
            let emp = data;
            localStorage.setItem('employeeData', JSON.stringify(emp));
            window.location.href = 'update.html';
        },
        error: function (xhr, exception) {
            alert("Error");
        }
    });
}

function updateEmployee() {
    let id = $('#employeeId').val();
    let firstName = htmlspecialchars($('#firstname').val());
    let lastName = htmlspecialchars($('#lastname').val());
    let email = htmlspecialchars($('#email').val());
    let telNo = htmlspecialchars($('#telno').val());

    if (!firstName) {
        alert("First Name is required.");
        return;
    }
    if (!lastName) {
        alert("Last Name is required.");
        return;
    }
    if (!email) {
        alert("Email is required.");
        return;
    }
    if (!telNo) {
        alert("Telephone Number is required.");
        return;
    }

    if (!validateInputs(firstName, lastName, email, telNo)) {
        return;
    }

    $.ajax({
        method: "PUT",
        contentType: "application/json",
        url: "http://localhost:8082/api/employees/" + id,
        async: true,
        data: JSON.stringify({
            "id": id,
            "firstName": firstName,
            "lastName": lastName,
            "email": email,
            "telephone": telNo
        }),
        success: function (data) {
            alert("Updated");
            window.location.href = 'view.html';
        },
        error: function (xhr, exception) {
            alert("Error");
        }
    });
}

function isFormEmpty(firstName, lastName, email, telNo) {
    return !firstName && !lastName && !email && !telNo;
}

function validateInputs(firstName, lastName, email, telNo) {
    const namePattern = /^[a-zA-Z']+$/;
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const telPattern = /^07\d{8}$/;

    if (!namePattern.test(firstName)) {
        alert("First name should only contain letters and ' symbol.");
        return false;
    }
    if (firstName.length > 10) {
        alert("First name should not exceed 10 characters.");
        return false;
    }
    if (!namePattern.test(lastName)) {
        alert("Last name should only contain letters and ' symbol.");
        return false;
    }
    if (lastName.length > 20) {
        alert("Last name should not exceed 20 characters.");
        return false;
    }
    if (!emailPattern.test(email)) {
        alert("Invalid email format.");
        return false;
    }
    if (email.length > 100) {
        alert("Email should not exceed 100 characters.");
        return false;
    }
    if (!telPattern.test(telNo)) {
        alert("Telephone number should contain 10 digits and start with 07.");
        return false;
    }
    if (telNo.length > 10) {
        alert("Telephone number should not exceed 10 characters.");
        return false;
    }
    return true;
}

function htmlspecialchars(str) {
    if (typeof str !== 'string') {
        return str;
    }
    return str.replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}

$(document).ready(function () {
    if (window.location.pathname.endsWith('update.html')) {
        let emp = JSON.parse(localStorage.getItem('employeeData'));
        if (emp) {
            $('#employeeId').val(emp.id);
            $('#firstname').val(emp.firstName);
            $('#lastname').val(emp.lastName);
            $('#email').val(emp.email);
            $('#telno').val(emp.telephone);
        }
    }
});