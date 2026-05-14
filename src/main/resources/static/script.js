let classiCache = [];
let studentiCache = [];
let editStudenteModal;

window.addEventListener("DOMContentLoaded", () => {
    editStudenteModal = new bootstrap.Modal(document.getElementById("editStudenteModal"));
    loadClassi();
    loadStudenti();
});

function toggleDarkMode() {
    document.body.classList.toggle("dark");
    document.body.classList.toggle("light");
}

/* ============================
   CARICAMENTO CLASSI
============================ */
function loadClassi() {
    fetch("/classe/lista")
        .then(res => res.json())
        .then(classi => {
            classiCache = classi;

            const tbody = document.querySelector("#tabellaClassi tbody");
            const select = document.getElementById("classeSelect");
            const filtro = document.getElementById("filtroClasse");
            const editSelect = document.getElementById("editClasseSelect");

            tbody.innerHTML = "";
            select.innerHTML = "";
            filtro.innerHTML = `<option value="">Tutte le classi</option>`;
            editSelect.innerHTML = "";

            classi.forEach(c => {
                tbody.innerHTML += `
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.sezione}</td>
                        <td>${c.numeroStudenti}</td>
                        <td>
                            <button class="btn btn-sm btn-danger" onclick="deleteClasse(${c.id})">Elimina</button>
                        </td>
                    </tr>
                `;

                const opt = `<option value="${c.id}">${c.sezione}</option>`;
                select.innerHTML += opt;
                filtro.innerHTML += opt;
                editSelect.innerHTML += opt;
            });

            aggiornaDashboard();
        });
}

/* ============================
   AGGIUNTA CLASSE
============================ */
function addClasse() {
    const dto = {
        sezione: document.getElementById("sezione").value,
        numeroStudenti: parseInt(document.getElementById("numeroStudenti").value)
    };

    fetch("/classe/insert", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    }).then(() => {
        document.getElementById("sezione").value = "";
        document.getElementById("numeroStudenti").value = "";
        loadClassi();
    });
}

/* ============================
   ELIMINA CLASSE (con blocco)
============================ */
function deleteClasse(id) {
    if (!confirm("Eliminare la classe?")) return;

    fetch(`/classe/delete/${id}`, { method: "DELETE" })
        .then(res => res.text())
        .then(msg => {

            // 🔥 BLOCCO ELIMINAZIONE SE CI SONO STUDENTI
            if (msg.includes("Impossibile eliminare")) {
                alert(msg);
                return;
            }

            loadClassi();
            loadStudenti();
        });
}

/* ============================
   CARICAMENTO STUDENTI
============================ */
function loadStudenti() {
    fetch("/studente/lista")
        .then(res => res.json())
        .then(studenti => {
            studentiCache = studenti;

            const filtroClasse = document.getElementById("filtroClasse").value;
            const tbody = document.querySelector("#tabellaStudenti tbody");
            tbody.innerHTML = "";

            let filtrati = studenti;
            if (filtroClasse) {
                filtrati = studenti.filter(s => String(s.classeId) === String(filtroClasse));
            }

            filtrati.forEach(s => {
                const nomeClasse = s.classeNome || "N/A";

                tbody.innerHTML += `
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.nome}</td>
                        <td>${s.cognome}</td>
                        <td>${s.eta}</td>
                        <td>${nomeClasse}</td>
                        <td>
                            <button class="btn btn-sm btn-primary me-1" onclick="openEditStudente(${s.id})">Modifica</button>
                            <button class="btn btn-sm btn-danger" onclick="deleteStudente(${s.id})">Elimina</button>
                        </td>
                    </tr>
                `;
            });

            aggiornaDashboard();
        });
}

/* ============================
   AGGIUNTA STUDENTE
============================ */
function addStudente() {
    const classeId = parseInt(document.getElementById("classeSelect").value);

    if (!classeId || isNaN(classeId)) {
        alert("Seleziona una classe!");
        return;
    }

    const dto = {
        nome: document.getElementById("nome").value,
        cognome: document.getElementById("cognome").value,
        eta: parseInt(document.getElementById("eta").value),
        classeId: classeId
    };

    fetch("/studente/insert", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    })
    .then(() => {
        document.getElementById("nome").value = "";
        document.getElementById("cognome").value = "";
        document.getElementById("eta").value = "";

        loadStudenti();
        loadClassi(); // 🔥 aggiorna numeroStudenti
    });
}

/* ============================
   ELIMINA STUDENTE
============================ */
function deleteStudente(id) {
    if (!confirm("Eliminare lo studente?")) return;

    fetch(`/studente/delete/${id}`, { method: "DELETE" })
        .then(() => {
            loadStudenti();
            loadClassi(); // 🔥 aggiorna numeroStudenti
        });
}

/* ============================
   MODIFICA STUDENTE
============================ */
function openEditStudente(id) {
    const s = studentiCache.find(st => st.id === id);
    if (!s) return;

    document.getElementById("editStudenteId").value = s.id;
    document.getElementById("editNome").value = s.nome;
    document.getElementById("editCognome").value = s.cognome;
    document.getElementById("editEta").value = s.eta;
    document.getElementById("editClasseSelect").value = s.classeId;

    editStudenteModal.show();
}

function salvaStudenteEdit() {
    const id = document.getElementById("editStudenteId").value;
    const classeId = parseInt(document.getElementById("editClasseSelect").value);

    if (!classeId || isNaN(classeId)) {
        alert("Seleziona una classe!");
        return;
    }

    const dto = {
        nome: document.getElementById("editNome").value,
        cognome: document.getElementById("editCognome").value,
        eta: parseInt(document.getElementById("editEta").value),
        classeId: classeId
    };

    fetch(`/studente/update/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    }).then(() => {
        editStudenteModal.hide();
        loadStudenti();
        loadClassi(); // 🔥 aggiorna numeroStudenti
    });
}

/* ============================
   DASHBOARD
============================ */
function aggiornaDashboard() {
    document.getElementById("totClassi").innerText = classiCache.length;
    document.getElementById("totStudenti").innerText = studentiCache.length;
}
