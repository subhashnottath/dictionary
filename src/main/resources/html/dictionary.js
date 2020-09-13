function lookup() {
    const url = '/lookup';
    (async () => {
        const word = document.getElementById("word").value;
        const rawResponse = await fetch(url, {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
            word: word,
            })
        });
        const response = await rawResponse.json();
        if (response.word == "Not found") {
            const table = document.getElementById("responseTable");
            table.innerHTML = "";
            let row = table.insertRow();
            let message = row.insertCell(0);
            message.innerHTML = "Word not found";
        } else {
            loadTableData(response.meanings);
        }
        document.getElementById("responseDiv").innerHTML = "";
        if (response.suggestions && response.suggestions.length > 0) {
            var similar = "Similar words &nbsp; : &nbsp; " + format(response.suggestions);
            document.getElementById("responseDiv").innerHTML = similar;
        }
    })();
}

function loadTableData(meanings) {
    const table = document.getElementById("responseTable");
    table.innerHTML = "";
    var i = 1;
    meanings.forEach(meaning => {
        let row0 = table.insertRow();
        let definitionLabel = row0.insertCell(0);
        var header = "<h4> Definition - " + i;
        if (meaning.speech_part != undefined) {
            header = header + "&nbsp&nbsp(" + meaning.speech_part + ") </h4>";
        }
        definitionLabel.innerHTML = header;
        definitionLabel.colSpan = 2;
        let row1 = table.insertRow();
        let definition = row1.insertCell(0);
        definition.innerHTML = startCase(meaning.def);
        definition.colSpan = 2;
        if (meaning.synonyms != undefined) {
            let row2 = table.insertRow();
            let synonyms = row2.insertCell(0);
            synonyms.innerHTML = "Synonyms &nbsp; : &nbsp;" + format(meaning.synonyms);
        }
        i++;
    });
}

function format(items) {
    var res = items[0];
    for (i = 1; i < items.length; i++) {
        res = res + ",&nbsp" + items[i];
    }
    return res;
}

function startCase(word) {
    return word[0].toUpperCase() + word.substr(1);
}

function handleKeyPress(e) {
    if (e.keyCode == 13) {
        lookup();
    }
}
