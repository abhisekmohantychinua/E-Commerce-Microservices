// COPY TO CLIP BOARD
// todo - to be fixed, Uncaught (in promise) DOMException: Failed to execute 'writeText' on 'Clipboard': Document is not focused.
let copyIcon = document.getElementById('copy-icon')
copyIcon.addEventListener('click', () => {
    let copyText = document.getElementById('copy-text').innerText
    navigator.clipboard.writeText(copyText).then()
})


// CHANGE PAYMENT TYPE
function changePayment(id, paymentType) {
    window.location.href = `/payments/${id}?paymentType=${paymentType.toString().toUpperCase()}`
}

document.querySelectorAll('input[name = paymentType]').forEach((paymentType) => {
    paymentType.addEventListener('change', (e) => {
        let id = document.getElementById('copy-text').innerText
        let paymentType = e.target.value
        changePayment(id, paymentType)
    })
})

// Payment redirection
let upiField = document.getElementsByClassName("upi");
for (let i = 0; i < upiField.length; i++) {
    let upi = upiField[i];
    upi.addEventListener('click', (e) => {
        e.preventDefault();

        // Create a form
        let form = document.createElement('form');
        let id = document.getElementById('copy-text').innerText
        form.method = 'POST';
        form.action = `/payments/${id}`;

        // Add paymentType input
        let paymentTypeInput = document.createElement('input');
        paymentTypeInput.type = 'hidden';
        paymentTypeInput.name = 'paymentType';
        paymentTypeInput.value = 'upi';
        form.appendChild(paymentTypeInput);

        // Append form to the body and submit
        document.body.appendChild(form);
        form.submit();
    });
}

// Payment redirection for Net Banking
let netBankingField = document.getElementsByClassName("net-banking-option");
for (let i = 0; i < netBankingField.length; i++) {
    let bankOption = netBankingField[i];
    bankOption.addEventListener('click', (e) => {
        e.preventDefault();

        let bankCode = bankOption.dataset.bankCode;

        // Create a form
        let form = document.createElement('form');
        let id = document.getElementById('copy-text').innerText
        form.method = 'POST';
        form.action = `/payments/${id}`;

        // Add paymentType input
        let paymentTypeInput = document.createElement('input');
        paymentTypeInput.type = 'hidden';
        paymentTypeInput.name = 'paymentType';
        paymentTypeInput.value = 'net_banking';
        form.appendChild(paymentTypeInput);

        // Add bankCode input
        let bankCodeInput = document.createElement('input');
        bankCodeInput.type = 'hidden';
        bankCodeInput.name = 'bankCode';
        bankCodeInput.value = bankCode;
        form.appendChild(bankCodeInput);

        // Append form to the body and submit
        document.body.appendChild(form);
        form.submit();
    });
}


