async function post(uri, body) {
    const response = await fetch(uri, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });

    return await response.json();
}