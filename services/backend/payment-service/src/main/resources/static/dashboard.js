let trigger = document.getElementById('trigger')
let menu = document.getElementById('menu')
trigger.addEventListener('mouseover', () => {
    menu.classList.remove('hidden')
})
menu.addEventListener('mouseover', () => {
    menu.classList.remove('hidden')
})
trigger.addEventListener('mouseout', () => {
    menu.classList.add('hidden')
})
menu.addEventListener('mouseout', () => {
    menu.classList.add('hidden')
})