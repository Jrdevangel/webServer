const BASE_URL = "http://localhost:8080";

async function fetchHomeMessage() {
    try {
        const response = await fetch(`${BASE_URL}/`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const message = await response.text();
        console.log("Home message from backend:", message);

        const homeMsgElement = document.getElementById("home-message");
        if (homeMsgElement) {
            homeMsgElement.textContent = message;
        }
    } catch (error) {
        console.error("Error fetching home message:", error);
    }
}

async function fetchDashboardMessage() {
    try {
        const response = await fetch(`${BASE_URL}/dashboard`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const message = await response.text();
        console.log("Dashboard message from backend:", message);

        const dashboardMsgElement = document.getElementById("dashboard-message");
        if (dashboardMsgElement) {
            dashboardMsgElement.textContent = message;
        }
    } catch (error) {
        console.error("Error fetching dashboard message:", error);
    }   
}

document.addEventListener("DOMContentLoaded", () => {
    fetchHomeMessage();
    fetchDashboardMessage();
});