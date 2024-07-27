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
