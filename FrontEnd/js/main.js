const BASE_URL = "http://localhost:8080";

async function fetchHomeMessage() {
    try {
        const response = await fetch(`${BASE_URL}/admin/dashboard`);
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
    
    if (loginForm) {
            loginForm.addEventListener("submit", async (event) => {
                event.preventDefault();

                const username = document.getElementById("username").value;
                const password = document.getElementById("password").value;                 
                   
                try {
                    const response = await fetch(`${BASE_URL}/login`, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify({ username, password }),
                    });

                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }

                    const data = await response.json();
                    console.log("Login success:", data);

                    if (data.token) {
                        localStorage.setItem("authToken", data.token);
                    }

                    window.location.href = "dashboard.html";
                } catch (error) {
                    console.error("Login Failed:", error);
                    alert("Login failed. Please check your credentials and try again.")
                }
            });
    }
});