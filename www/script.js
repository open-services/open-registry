function toggleActive (el) {
  const target = el.dataset.target
  const $target = document.getElementById(target)
  el.classList.toggle('is-active')
  $target.classList.toggle('is-active')
}

document.addEventListener('DOMContentLoaded', () => {
  const $navbarBurgers = document.querySelectorAll('.navbar-burger')
  const $navbarItems = document.querySelectorAll('.navbar-item')

  $navbarBurgers.forEach(el => {
    el.addEventListener('click', () => {
      toggleActive(el)
    })
  })
  $navbarItems.forEach(el => {
    el.addEventListener('click', () => {
      toggleActive(document.querySelector('.navbar-burger'))
    })
  })
})
