import pandas as pd
import openpyxl
from datetime import date

myHeader = ["name", "email", "contactNum", "parentContactNum" ,"dob", "age", "registeredDate", "unitName"]

def write(df):
    writer = pd.ExcelWriter('test.xls', engine='openpyxl')
    df.to_excel(writer, sheet_name='welcome', index=False, header=myHeader)
    writer.save()

def read():
    return pd.read_excel('test.xls', engine='openpyxl')

def addRow(myrow):
    try:
        df = read()
        if df['email'].str.contains(myrow[1]).any():
            raise Exception('Email already registered')
    except FileNotFoundError:
        df = pd.DataFrame(columns=myHeader)
    
    df.loc[len(df)] = myrow
    write(df)






def main():
    today = date.today()
    addRow(myrow = ["tommy", "jesvddminjoril98@yahoo.co.in", "07744333222", "0553534342", "24/02/1998", 24, today, "NWLU"]);
    print(read())

if __name__ == "__main__":
    main()