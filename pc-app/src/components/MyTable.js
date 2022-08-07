import React from 'react';

const MyTable = props =>  {

      if(props.data){
        var heading = props.data.heading;
        var body = props.data.body;
      }
      return (
          <table style={{ width: 1550 }}>
              <thead>
                  <tr>
                      {heading.map(head => <th>{head}</th>)}
                  </tr>
              </thead>
              <tbody>
                  {body.map(row => <TableRow row={row} />)}
              </tbody>
          </table>
      );
}

const TableRow = props => {

  var row = props.row;
  return (
      <tr>
          {row.map(val => <td>{val}</td>)}
      </tr>
  )
  
}

export default MyTable;