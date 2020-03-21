$(document).ready(() => {

    const criteria = ["Ціна", "Запас ходу", "Швидкість", "Зарядка", "Місткість", "Комфорт"];
    const cars = ["Tesla Model 3", "Nissan Leaf", "VW e-Golf", "Hyundai Ioniq"];
    const validValues = ["1", "2", "3", "4", "5", "6", "7", "8", "9"];
    const validInverseValues = ["1", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"];

    const criteriaList = $('#criteria-list');
    const matrixContainer = $('#matrix-container');
    const resultContainer = $('#result');
    const loader = $('#loader');
    loader.hide();

    renderData();

    $(document).on('click', '.criteria-delete', (e) => {
        let index = $(e.currentTarget).data('index') - 1;
        criteria.splice(index, 1);
        renderData();
    });

    $(document).on('change', '.criteria-input', (e) => {
        let self = $(e.currentTarget);
        let index = self.data('index') - 1;
        criteria[index] = self.val();
        renderData();
    });

    $(document).on('change', '.matrix-cell', (e) => {
        let self = $(e.currentTarget);
        let opposite = self.data('opposite');
        let tableId = self.data('table');
        let value = self.val();

        if (validValues.indexOf(value) === -1 && validInverseValues.indexOf(value) === -1) {
            self.addClass('uk-form-danger');
        } else {
            self.removeClass('uk-form-danger');
            let cell = $(`#${tableId}-cell-${opposite}`);
            cell.removeClass('uk-form-danger');
            let oppositeValue = validInverseValues[validValues.indexOf(value)];

            if (validInverseValues.indexOf(value) !== -1) {
                oppositeValue = validValues[validInverseValues.indexOf(value)];
            }

            cell.val(oppositeValue);
        }

    });

    $('#add-criteria').click(() => {
        criteria.push('');
        renderData()
    });

    function parseNumber(val) {
        if (val.length === 1) {
            return parseInt(val);
        } else {
            let values = val.split('/');
            return parseFloat((parseInt(values[0])/parseInt(values[1])).toFixed(2));
        }
    }

    $('#submit-matrix').click(() => {
        // check if all cell have values
        let invalidCells = $('.matrix-cell').filter((i, el) => {
            return validValues.indexOf(el.value) === -1 && validInverseValues.indexOf(el.value) === -1;
        });
        if (invalidCells.length) {
            $(invalidCells).each((i, el) => {
                $(el).addClass('uk-form-danger');
            });
            return;
        }

        let criteriaValues = $('#criteria-matrix .matrix-cell').map((i, el) => {
            return parseNumber(el.value);
        });

        let criteriaData = [];
        let begin = 0,
            end = criteria.length;
        while (end <= Math.pow(criteria.length,2)) {
            let iterator = criteriaValues.slice(begin, end);
            let data = [];
            for (let value of iterator) {
                data.push(value);
            }
            criteriaData.push(data);
            begin = end;
            end += criteria.length;
        }

        let alternativesData = [];
        for (let i = 0; i < criteria.length; i++) {
            let alternativesValues = $(`#criteria${i}-matrix .matrix-cell`).map((i, el) => {
                return parseNumber(el.value);
            });
            let alternativesTempData = [];
            let begin = 0,
                end = cars.length;
            while (end <= Math.pow(cars.length,2)) {
                let iterator = alternativesValues.slice(begin, end);
                let data = [];
                for (let value of iterator) {
                    data.push(value);
                }
                alternativesTempData.push(data);
                begin = end;
                end += cars.length;
            }

            alternativesData.push(alternativesTempData);

        }

        let body = {'criteria': criteriaData, 'alternatives': alternativesData};

        loader.show();

        $.ajax({
            url: '/ahp',
            headers: {
                'Content-Type':'application/json'
            },
            method: 'POST',
            dataType: 'json',
            data: JSON.stringify(body),
            success: function(data){
                loader.hide();
                let carsRating = [];
                for (let i = 0; i < cars.length; i++) {
                    carsRating.push({'car': cars[i], 'rating': data[i]});
                }
                carsRating.sort((a, b) => {
                    return b.rating - a.rating;
                });

                let html = `<div class="uk-container" uk-scrollspy="cls: uk-animation-fade">
                    <h2>Результати</h2>
                    <ul class="uk-list">`;
                for (let i = 0; i < carsRating.length; i++) {
                    html += `<li>${i+1}. ${carsRating[i].car} - ${carsRating[i].rating.toFixed(2)} % </li>`;
                }
                html += `</ul></div>`;
                resultContainer.html(html);
            },
            error: function(err) {
                console.log(err);
            }
        });
    });

    function renderData() {
        renderCriteriaList();
        renderTables();
    }

    function renderCriteriaList() {
        let html = '';
        criteria.forEach((criteria, index) => {
            index++;
            html += `<li>
                    <div class="uk-margin uk-form-horizontal">
                        <label class="uk-form-label" for="criteria${index}">${index}.</label>
                        <div class="uk-form-controls uk-width-1-4@l uk-width-1-2@s">
                            <div class="uk-inline">
                                <a class="uk-form-icon uk-form-icon-flip criteria-delete" href="javascript:void(0);" data-index="${index}" uk-icon="icon: close"></a>
                                <input class="uk-input uk-form-blank criteria-input" id="criteria${index}" type="text" value="${criteria}" data-index="${index}" placeholder="Критерій ${index}">
                            </div>
                        </div>
                    </div>
                </li>`;
        });

        criteriaList.html(html);
    }

    function renderTables() {
        // render criteria
        matrixContainer.html(
            `<div class="uk-container">
            <h2>Оцініть відносні переваги критеріїв</h2>
            <div class="uk-overflow-auto" uk-margin>
                <table id="criteria-matrix" class="uk-table uk-table-responsive">
                </table>
            </div>
        </div>`);
        renderTable('criteria-matrix', 'Критерії', criteria);

        // render alternatives per criteria tables
        for (let i = 0; i < criteria.length; i++) {
            matrixContainer.append(
                `<div class="uk-container">
                <h2>Оцініть відносні переваги автомобілів за критерієм "${criteria[i]}"</h2>
                <div class="uk-overflow-auto" uk-margin>
                    <table id="criteria${i}-matrix" class="uk-table uk-table-responsive">
                    </table>
                </div>
            </div>`);
            renderTable(`criteria${i}-matrix`, criteria[i], cars);
        }
    }

    function renderTable(tableId, tableTitle, titles) {
        let table = $(`#${tableId}`);
        let row, cell;

        // append title first row
        row = $('<tr />');
        table.append(row);
        cell = $('<th>' + tableTitle + '</th>')
        row.append(cell);
        for (let idx in titles) {
            cell = $('<th>' + titles[idx] + '</th>')
            row.append(cell);
        }

        // other rows
        for (let i = 0; i < titles.length; i++) {
            row = $('<tr />');
            table.append(row);
            cell = $('<th>' + titles[i] + '</th>');
            row.append(cell);

            for (let j = 0; j < titles.length; j++) {
                let disabled = '';
                let value = '';
                if (titles[i] === titles[j]) {
                    disabled = 'disabled';
                    value = 1;
                }
                let opposite = j + '' + i;
                let cellId = i + '' + j;
                cell = $('<td>' +
                    `<input id="${tableId}-cell-${cellId}" class="uk-input uk-form-width-xsmall matrix-cell" type="text" data-opposite="${opposite}" data-table="${tableId}" placeholder="" value="${value}" max="9" min="0"` + disabled + '>'
                    + '</td>');
                row.append(cell);
            }

        }
    }


});