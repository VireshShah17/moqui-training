import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityValue

// This script assumes it's running in the context of a Moqui service-call
ExecutionContext ec = context.ec

// Input parameters
def trainingName = context.trainingName
def trainingDate = context.trainingDate
def trainingPrice = context.trainingPrice
def trainingDuration = context.trainingDuration

// Validate required fields
if (!trainingName) {
    ec.message.addError("Training name is required.")
    return
}

if (!trainingDate) {
    ec.message.addError("Training date is required.")
    return
}

// Explicitly generate a unique ID
def trainingID = ec.entity.sequencedIdPrimary("moqui.training.MoquiTraining", null, null)

// Create the MoquiTraining entity record
EntityValue trainingRecord = ec.entity.makeValue("moqui.training.MoquiTraining")

trainingRecord.set("trainingID", trainingID) // Explicitly set trainingId
trainingRecord.set("trainingName", trainingName)
trainingRecord.set("trainingDate", trainingDate)

if (trainingPrice != null) trainingRecord.set("trainingPrice", trainingPrice)
if (trainingDuration != null) trainingRecord.set("trainingDuration", trainingDuration)

// Save the record
trainingRecord = trainingRecord.create()

// Set the output parameter
context.trainingID = trainingRecord.get("trainingID")
