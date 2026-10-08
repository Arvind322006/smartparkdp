// Client-side interactions for Smart Parking Management System
document.addEventListener("DOMContentLoaded", function () {
    console.log("Smart Parking Management System initialized.");

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = "opacity 0.5s ease";
            alert.style.opacity = "0";
            setTimeout(() => alert.remove(), 500);
        }, 6000);
    });
});
