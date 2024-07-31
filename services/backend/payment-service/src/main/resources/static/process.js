let form = document.getElementById('status-form')

function onSuccess() {
    form.action = '/payments/process'
    form.method = 'POST'
    let statusInput = document.createElement('input');
    statusInput.type = 'hidden';
    statusInput.name = 'status';
    statusInput.value = 'true';
    form.appendChild(statusInput);
    form.submit();
}

function onFailure() {
    form.action = '/payments/process'
    form.method = 'POST'
    let statusInput = document.createElement('input');
    statusInput.type = 'hidden';
    statusInput.name = 'status';
    statusInput.value = 'false';
    form.appendChild(statusInput);
    form.submit();
}

